package TP1.storeApplication.service;

import TP1.storeApplication.entity.Client;
import TP1.storeApplication.entity.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void createClient(String email, String password, String nom, String prenom){
        Client client = new Client(email,password,nom,prenom);
        clientRepository.save(client);
    }

    public Client connexion(String email, String password){
        Client c = clientRepository.findById(email).orElse(null);
        if(c != null && c.getPassword().equals(password)){
            return c;
        }
        return null;
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findById(email).orElse(null);
    }
}
