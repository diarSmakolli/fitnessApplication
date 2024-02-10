package com.example.fitness_app.User;
import com.example.fitness_app.config.KeycloakConfig;
import com.example.fitness_app.common.CommonResponseDTO;
import com.example.fitness_app.common.ValidationUtilsDTO;
import com.example.fitness_app.exceptions.*;
import org.apache.catalina.User;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    private final Keycloak keycloak;
    private KeycloakConfig keycloakConfig;
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(Keycloak keycloak, KeycloakConfig keycloakConfig, UserRepository userRepository) {
        this.keycloak = keycloak;
        this.keycloakConfig = keycloakConfig;
        this.userRepository = userRepository;
    }

    public CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }


    private RoleRepresentation getDefaultRole(){
        return keycloak.realm(keycloakConfig.getRealm()).clients()
                .get(keycloakConfig.getPublicClientId()).roles().get("user").toRepresentation();
    }

    public void createUser(UserDTOSave userDTOSave) {
        try{
            CredentialRepresentation credential = createPasswordCredentials(userDTOSave.getPassword());
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userDTOSave.getUsername());
            user.setFirstName(userDTOSave.getFirstname());
            user.setLastName(userDTOSave.getLastname());
            user.setEmail(userDTOSave.getEmail());
            user.setCredentials(Collections.singletonList(credential));
            user.setEnabled(true);

            UsersResource instance = keycloak.realm(keycloakConfig.getRealm()).users();

            Response response = instance.create(user);

            if(response.getStatus() != 201) {
                throw new InternalServerErrorException("Could not create user");
            }

            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            UserResource userResource = instance.get(userId);
            var defaultRole = getDefaultRole();
            userResource.roles().clientLevel(keycloakConfig.getPublicClientId()).add(Collections.singletonList(defaultRole));


            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            userEntity.setUsername(userDTOSave.getUsername());
            userEntity.setEmail(userDTOSave.getEmail());
            userEntity.setFirstname(userDTOSave.getFirstname());
            userEntity.setLastname(userDTOSave.getLastname());
            userEntity.setProfilePicture(userDTOSave.getProfilePicture());

            userRepository.save(userEntity);

        }
        catch (DataAccessException ex) {
            // Handle data access exceptions
            logAndThrowInternalServerError("Error saving user", ex);
        } catch (BadRequestException ex) {
            // Handle validation exceptions
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
        }
    }



    @Override
    public UserEntity findUserById(String id) {
        UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> {
            logger.warning("Progress not found: " + id);
            return new EntityNotFoundException("Progress not found");
        });

        return user;
    }

    @Override
    @Transactional
    public UserEntity updateProfile(String id, UserDTO userDTO) {

            UserEntity existingUser = findUserById(id);

            if(!userDTO.getPassword().equals(existingUser.getPassword())) {
                throw new NotFoundException("User not found with id");
            }

            existingUser.setFirstname(userDTO.getFirstname());
            existingUser.setLastname(userDTO.getLastname());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setProfilePicture(userDTO.getProfilePicture());

            if(userDTO.getNewPassword() != null || userDTO.getNewPassword() == "" && !userDTO.getNewPassword().isEmpty()) {
                existingUser.setPassword(userDTO.getNewPassword());
            }

            return userRepository.save(existingUser);

    }



    
    public String loginUser(UserLoginDTO userLoginDTO) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakConfig.getServerUrl())
                    .realm(keycloakConfig.getRealm())
                    .clientId("rest-api")
                    .username(userLoginDTO.getUsername())
                    .password(userLoginDTO.getPassword())
                    .grantType(OAuth2Constants.PASSWORD)
                    .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                    .build();

            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();

            return tokenResponse.getToken();
        }
        catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error saving user", ex);
            return null;
        } catch (BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return null;
        }
    }



    private void logAndThrowEntityNotFoundException(String message) {
        logger.warning(message);
        throw new EntityNotFoundException(message);
    }

    private void logAndThrowInternalServerError(String message, Exception ex) {
        logger.severe(message + ": " + ex.getMessage());
        throw new InternalServerErrorException(message);
    }

    private void logAndThrowBadRequest(String message) {
        logger.warning(message);
        throw new BadRequestException(message);
    }

}
