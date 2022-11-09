package tn.esprit.rh.achat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.CategorieFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;

import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IFournisseurServiceTest {
    @Mock
    FournisseurRepository fournisseurRepository;
    @InjectMocks
    FournisseurServiceImpl fournisseurServiceImp;

    Fournisseur fournisseur = new Fournisseur("code", "libelle", CategorieFournisseur.ORDINAIRE);
    List<Fournisseur> fournisseurList = new ArrayList<Fournisseur>(){
        {
            add(new Fournisseur("code1", "libelle1", CategorieFournisseur.ORDINAIRE));
            add(new Fournisseur("code2", "libelle2", CategorieFournisseur.CONVENTIONNE));
        }
    };
    @Test
    @Order(1)
    void addFournisseur() {
        Mockito.when(fournisseurRepository.save(fournisseur)).thenReturn(fournisseur);
        Fournisseur f = fournisseurServiceImp.addFournisseur(fournisseur);
        assertNotNull(f);
    }
    @Test
    @Order(2)
    void retrieveAllFournisseurs() {
        Mockito.when(fournisseurRepository.findAll()).thenReturn(fournisseurList);
        List<Fournisseur> lf = fournisseurServiceImp.retrieveAllFournisseurs();
        assertEquals(2, lf.size());
    }
    @Test
    @Order(3)
    void retrieveFournisseur() {
        Mockito.when(fournisseurRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fournisseur));
        Fournisseur f = fournisseurServiceImp.retrieveFournisseur(2L);
        assertNotNull(f);
    }
    @Test
    @Order(5)
    void deleteFournisseur() {
        Mockito.doNothing().when(fournisseurRepository).deleteById(Mockito.anyLong());
        fournisseurServiceImp.deleteFournisseur(3L);
        Mockito.verify(fournisseurRepository, Mockito.times(1)).deleteById(3L);
    }
   /* @Test
    @Order(4)
    void updateFournisseur() {
        Mockito.when(fournisseurRepository.save(fournisseur)).thenReturn(fournisseur);
        Fournisseur f = fournisseurServiceImp.addFournisseur(fournisseur);
        f.setCode("toutou");
        f =fournisseurServiceImp.updateFournisseur(f);
        Fournisseur updatedValue = fournisseurServiceImp.retrieveFournisseur(f.getIdFournisseur());
        assertNotEquals(updatedValue,fournisseur);
    }*/


//    @Test
//    @Order(6)
//    void updateFournisseur() {
//
//    }

//    @Test
//    @Order(4)
//    void assignSecteurActiviteToFournisseur() {
//        SecteurActivite sa = new SecteurActivite("aa","bb");
//        SecteurActivite saAdded = iSecteurActiviteService.addSecteurActivite(sa);
//        fournisseurService.assignSecteurActiviteToFournisseur(saAdded.getIdSecteurActivite(),1L);
//        assertNotNull(fournisseurService.retrieveFournisseur(1L).getSecteurActivites());
//        iSecteurActiviteService.deleteSecteurActivite(saAdded.getIdSecteurActivite());
//    }
}
