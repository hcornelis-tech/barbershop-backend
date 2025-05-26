package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetUsersUseCase;
import nl.fontys.s3.clipfadeapplication.domain.response.GetUserResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetUsersResponse;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsersUseCaseImpl implements GetUsersUseCase {
    private UserRepository userRepository;
    private AppointmentRepository appointmentRepository;

    @Override
    public GetUsersResponse getUsers() {
        List<UserEntity> results = userRepository.findAll();

        final GetUsersResponse getUsersResponse = new GetUsersResponse();
        List<GetUserResponse> users = results
                .stream()
                .map(UserConverter::convert)
                .toList();

        getUsersResponse.setUsers(users);

        return getUsersResponse;
    }

    @Override
    public GetUsersResponse getUsersByBarber(int id) {
        List<UserEntity> results = userRepository.findByBarber(Long.valueOf(id));

        final GetUsersResponse getUsersResponse = new GetUsersResponse();
        List<GetUserResponse> users = results.
                stream()
                .map(UserConverter::convert)
                .toList();

        getUsersResponse.setUsers(users);

        return getUsersResponse;
    }
}
