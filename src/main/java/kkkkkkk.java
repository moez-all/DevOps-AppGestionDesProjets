import jdk.jfr.events.CertificateId;
import sun.jvm.hotspot.debugger.cdbg.EnumType;

Enum
Specialite.java
public enum Specialite{
    INFORMATIQUE, TELECOMMUNICATIONS, MECANIQUE
}
TypeStage.java
public enum TypeStage{
    INITIATION, PERFECTIONNEMENT, PFE
}
-----------------------------------------------------
Classe Etudiant:
@Entity
@Getter
@Setter
@NoArgsconstructors
@Allargsconstructor
@Tostring
public class Etudiant{
    @Id
    @Generatedvalue(strategy=GenerationType.IDENTITY)
    private integer idEtudiant;
    private String nom;
    private String prenom;
    private String email;
@ManyToOne(mappedBy="Encadrent",cascade=cascadeType.All)
@JoinColumn(name = "idEncadrant") // ou nom de colonne souhaité
private Encadrant encadrant;
@OneToMany(mappedBy="Stage",cascade=cascadeType.All)
@JoinColumn(name = "idStage") // ou nom de colonne souhaité
private List<Encadrent.Stage> stage;

}
-----------------------------------------------------
Classe Encadrent:
@Entity
@Getter
@Setter
@NoArgsconstructors
@Allargsconstructor
@Tostring
public class Encadrent{
    @Id
    @Generatedvalue(strategy=GenerationType.IDENTITY)
    private integer idEncadrent;
    private String nom;
    @Enumerated(EnumType.STRING)
    private Specialite  specialite;

-----------------------------------------------------
    Classe Stage:
    @Entity
    @Getter
    @Setter
    @NoArgsconstructors
    @Allargsconstructor
    @Tostring
    public class Stage{
        @Id
        @Generatedvalue(strategy=GenerationType.IDENTITY)
        private integer idstage;
        private String sujet;
        private Integer dureeSemaines;
        private Boolean valide;
        @Enumerated(EnumType.STRING)
        private TypeStage typeStage;
        @ManyToOne(mappedBy="Etudiant",cascade=cascadeType.All)
        @JoinColumn(name = "idetudiant") // ou nom de colonne souhaité
        private Etudiant etudiant;

    }
}
---------------------------------------------------------------
partie 2
question a
Public interface EtudiantRepository extends JpaRepository<Etudiant,Integer>
-----
Pblic class EtudiantServiceImpl implements EtudiantService{
    private  EtudiantRepository  etudiantRepository;

    public Etudiant ajouterEtudiant(Etudiant etudiant){
        return etudiantRepository.save(etudiant);
    }
}
-----
@RetController
@RequestMapping("/Etudiant")
@AllArgsConstructor
public class EtudiantController{
    private  EtudiantServiceImpl etudiantService;
    @PostMapping("/ajouterEtudiant")
    public Etudiant ajouterEtudiant(@RequestBody Etudiant etudiant)
}
question b
Public interface EncadrentRepository extends JpaRepository<Encadrent,Integer>
-----
Pblic class EncadrentServiceImpl implements EncadrentService{
    private  EncadrentRepository  encadrentRepository;

    public Encadrent ajouterEncadrent(Encadrent encadrent){
        return encadrentRepository.save(encadrent);
    }
}
-----
@RetController
@RequestMapping("/Encadrent")
@AllArgsConstructor
public class EncadretController{
    private  EncadrentServiceImpl encadrentService;
    @PostMapping("/ajouterEncadrent")
    public Encadrent ajouterEncadrent(@RequestBody Encadrent encadrent)
}

----------------
question d
        service
@override
public void affecterEtudiantEncadrant(Integer idEtudiant, Integer idEncadrent){
    Etudiant e = EtudiantRepository.findById(idEtudiant).orElse(null);
    Encadrent en = EncadrentRepository.findById(idEncadrent).orElse(null);
    if (e != null && en != null) {
        e.setClasse(en);
        EtudiantRepository.save(e);
    }
}
@PutMapping("/affecterEtudiantEncadrant/{idEtudiant}/{idEncadrent}")
public void affecterEtudiantEncadrant(@PathVariable Integer idEtudiant,
                                      @PathVariable Integer idEncadrent) {
    examenService.affecterEtudiantEncadrant(idEtudiant,idEncadrant);
}
---------------------------------------------------------
question e
@Query("SELECT COUNT(s) FROM Stage s WHERE s.TypeStage = :type")
Integer countStagebytype(@Param("Typestage") Typestage typestage);
------
@Override
public Integer nbStagesValidesParType(TypeStage type) {
    return stageRepository.countStagebytype(type);
}------------
@GetMapping("/nbcountStagebytype/{type}")
public Integer nbcountStagebytype(@PathVariable TypeStage type ) {
    return etudiantService.nbcountStagebytype(type );
}
------------------------
question f
@Override
public void desaffecterEtudiantEncadrant(Integer idEtudiant) {
    Etudiant e = EtudiantRepository.findById(idEtudiant).orElse(null);
    if (e != null) {
        e.setClasse(null);
        EtudiantRepository.save(e);
    }
}
-
@PutMapping("/desaffecterEtudiantEncadrant/{idEtudiant}")
public void desaffecterEtudiantEncadrant(@PathVariable Integer idEtudiant) {
    Etudiant.Service.desaffecterEtudiantEncadrant(idEtudiant);
}

























