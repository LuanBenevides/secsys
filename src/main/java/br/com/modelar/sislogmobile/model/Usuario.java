package br.com.modelar.sislogmobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    private String password;

    @Column(name = "token_time")
    private Integer tokenExpiracao;
    @Column(name = "uid")
    private String uuid;

    {
        tokenExpiracao = 600000;
        uuid = "020996d9-50b2-4720-a1f2-a54a767306fa";
    }
}
