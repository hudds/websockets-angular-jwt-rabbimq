package dev.hudsonprojects.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Criado para testar configuracao, irei remover
 */
@Deprecated(forRemoval = true)
@RequestMapping("test")
@RestController
public class TestEnpoint {

    private static String instanceId;


    @GetMapping("instance-id")
    @ResponseStatus(HttpStatus.OK)
    public synchronized String getInstanceId(){
        if(instanceId == null){
            instanceId = UUID.randomUUID().toString();
        }
        return instanceId;
    }
}
