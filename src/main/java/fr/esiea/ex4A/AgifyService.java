package fr.esiea.ex4A;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;


@Service
public class AgifyService {

    public final fr.esiea.ex4A.AgifyClient agifyClient;
    public final UserRepository userRepository;

    public AgifyService (fr.esiea.ex4A.AgifyClient agifyClient, UserRepository userRepository)
    {
        this.agifyClient = agifyClient;
        this.userRepository = userRepository;
    }

    public void addUser(User user) throws IOException {
        if (userRepository.users.get(user.getId()) == null) {
            AgifyUser userA = agifyClient.getAgifyUser(user.username, user.country).execute().body();
            userRepository.addUser(user, userA);
        }
    }

    public ArrayList<User> matchFor(String username, String country)
    {
        User user = userRepository.users.get(username + country);
        if (user != null) {
            return userRepository.matchUsers(user);
        }
        else
        {
            return new ArrayList<User>();
        }
    }
}
