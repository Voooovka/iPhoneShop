package com.javovka.userservice.api;

import com.javovka.userservice.models.Iphone;
import com.javovka.userservice.service.IphoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/iphones")
public class IphoneController {

    private final IphoneService iphoneService;

    @GetMapping
    public ResponseEntity<List<Iphone>> getIphones(){
        return ResponseEntity.ok().body(iphoneService.getIphones());
    }

    @PostMapping
    public void saveIphone(@RequestBody Iphone iphone) {
        iphoneService.saveIphone(iphone);
    }

}
