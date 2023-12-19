package com.bikehub.model.mapper;

import com.bikehub.model.dto.UserRegisterDTO;
import com.bikehub.model.entity.User;
import com.bikehub.model.view.UserProfileView;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-19T14:42:33+0200",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class UserMappingImpl implements UserMapping {

    @Override
    public User userDtoToUser(UserRegisterDTO userRegisterDTO) {
        if ( userRegisterDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userRegisterDTO.getUsername() );
        user.setFirstName( userRegisterDTO.getFirstName() );
        user.setLastName( userRegisterDTO.getLastName() );
        user.setAge( userRegisterDTO.getAge() );
        user.setEmail( userRegisterDTO.getEmail() );
        user.setPassword( userRegisterDTO.getPassword() );

        user.setActive( true );

        return user;
    }

    @Override
    public UserProfileView userToProfileView(User user) {
        if ( user == null ) {
            return null;
        }

        UserProfileView userProfileView = new UserProfileView();

        userProfileView.setUsername( user.getUsername() );
        userProfileView.setFirstName( user.getFirstName() );
        userProfileView.setLastName( user.getLastName() );
        userProfileView.setAge( user.getAge() );
        userProfileView.setEmail( user.getEmail() );

        return userProfileView;
    }
}
