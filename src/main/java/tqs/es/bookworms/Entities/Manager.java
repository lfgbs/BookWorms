package tqs.es.bookworms.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Indica que só deve haver uma ocorrência de cada email na base de dados. Assim podemos usá-lo para o login
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    @ElementCollection //lista de localizações
    private List<Long> locationsIds = new ArrayList<Long>();
    @ElementCollection //lista de compras efetuadas
    private List<Long> orderIds = new ArrayList<Long>();

}
