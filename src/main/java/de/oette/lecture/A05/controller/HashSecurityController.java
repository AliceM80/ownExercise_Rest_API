package de.oette.lecture.A05.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value ="(hash-secured")
@Validated
public class HashSecurityController {

  private static final String SALT = "934jshdfjk83ß93skjskjdjhieowekowp03842984";  //SALT wird noch zusätzlich miteingebunden, zum Hash

  @GetMapping
  public String getAccess(@RequestParam @NotNull String user,    //user muss übergeben werden
                          @RequestParam @NotNull String hash,
                          @RequestParam @NotNull long timestampValidUntil) throws CustomAccessDeniedException {  //hash muss übergeben werden

    long now = System.currentTimeMillis();
    if(timestampValidUntil < now){
      throw new CustomAccessDeniedException("No longer valid");
    }

    String calculatedHash = calculatedHash(user, SALT, String.valueOf(timestampValidUntil));
    if (calculatedHash.equals(hash)) {
      return "Access granted";
    } else {
      throw new CustomAccessDeniedException("Wrong hash");

    }
  }

  private String calculatedHash(String... strings) {   //(String... strings) mehrere Strings können übergeben werden
    try{
    String concat = String.join("#", strings);  //mehrere Strings joine/concatiniere ich zu einem String zusammen #nicht zwingend notwendig
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");  //hasherzeuger über Java, SHA sicherer als manch andere
    byte[] digest = messageDigest.digest(concat.getBytes(Charset.forName("UTF-8")));  //wandle meinen string in bytes um
    return String.copyValueOf(Hex.encode(digest));   //Hilfsfunktion von StringSecuritypaket, mit hilfe von Hex kann ich entcoden, String
      //man kann auch irgendeine Bibliothek anbinden, oder mit wenigen zeilen java ausimplementieren

  } catch(NoSuchAlgorithmException e){
          throw new RuntimeException(e);
  }
}

//Damit ich jetzt mit meinem hash arbeiten kann, muss ich mir natürlich die Has auch ausgeben, dazu erzeuge ich folgende Methode

public static void main(String[] args) {
  long valid60Seconds = System.currentTimeMillis() + 60*1000;
  System.out.println(valid60Seconds);

  String calculatedHash = new HashSecurityController().calculatedHash("Max", SALT, String.valueOf(valid60Seconds));
  System.out.println(calculatedHash);

}

 /* private String calculatedHash(String user){   //(String user) ein Strings wird übergeben
    return "-";
  }*/

@ResponseStatus(code = HttpStatus.FORBIDDEN)
    private class CustomAccessDeniedException extends AccessDeniedException {
     public CustomAccessDeniedException(String msg) {
     super(msg);
     }
    }
  }

  //zeitlich begrenzter Zugriff durch Hash des Ablaufdatums auf eine URL - kurzfristig gültige URL erzeugen
  //java script CLient, der im Browser läuft, ideal für vom Browser gecachete Dateien z.b. für Bilder
