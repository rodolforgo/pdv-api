package com.pdvapi.services;

import com.pdvapi.dtos.UserDTO;
import com.pdvapi.entities.Sale;
import com.pdvapi.entities.User;
import com.pdvapi.exceptions.NoItemException;
import com.pdvapi.repositories.SaleRepository;
import com.pdvapi.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper mapper = new ModelMapper();

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user ->
                new UserDTO(user.getId(), user.getName(), user.isEnabled())).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO userDTO) {
        User userToSave = mapper.map(userDTO, User.class);
        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnabled());
    }

    public UserDTO findById(Long id) {
        Optional<User> optional = userRepository.findById(id);

        if (!optional.isPresent()) {
            throw new NoItemException("Usuário não encontrado.");
        }

        User user = optional.get();
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }

    public UserDTO update(UserDTO userDTO) {
        Optional<User> userExists = userRepository.findById(userDTO.getId());

        if (!userExists.isPresent()) {
            throw new NoItemException("Usuário não  encontrado.");
        }

        User userToEdit = mapper.map(userDTO, User.class);

        userRepository.save(userToEdit);
        return new UserDTO(userToEdit.getId(), userToEdit.getName(), userToEdit.isEnabled());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
