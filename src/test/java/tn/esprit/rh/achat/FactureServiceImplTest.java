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
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.FactureServiceImpl;
import tn.esprit.rh.achat.services.IFactureService;
import tn.esprit.rh.achat.services.IOperateurService;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FactureServiceImplTest {

    @Autowired
    IFactureService factureService;
    @Autowired
    IOperateurService iOperateurService;
    @Mock
    FactureRepository factureRepository;
    @InjectMocks
    FactureServiceImpl factureServiceImp;

    Facture facture = new Facture((float) 7.4, (float) 99.2,new Date(),new Date(),false);
    List<Facture> listFacture = new ArrayList<Facture>(){
        {
            add(new Facture((float) 6.4, (float) 99.2,new Date(),new Date(),true));
            add(new Facture((float) 7.9, (float) 99.2,new Date(),new Date(),true));
        }
    };

    @Test
    @Order(1)
    void addFacture(){
        Mockito.when(factureRepository.save(facture)).thenReturn(facture);
        Facture facture1 = factureServiceImp.addFacture(facture);
        assertNotNull(facture1);
    }
    @Test
    @Order(2)
    void retrieveAllFactures(){
        Mockito.when(factureRepository.findAll()).thenReturn(listFacture);
        List<Facture> listFacture1 = factureServiceImp.retrieveAllFactures();
        assertTrue(listFacture1.size()>0);
    }
    @Test
    @Order(3)
    void retrieveFacture() {
        Mockito.when(factureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(facture));
        Facture facture1 = factureServiceImp.retrieveFacture(2L);
        assertNotNull(facture1);
    }

    @Test
    @Order(4)
    void cancelFacture(){
        Mockito.doNothing().when(factureRepository).updateFacture(Mockito.anyLong());
        factureServiceImp.cancelFacture(1L);
        Mockito.verify(factureRepository, Mockito.times(1)).updateFacture(1L);
    }

//    @Test
//    @Order(6)
//    void assign(){
//        FactureServiceImpl fMock = Mockito.mock(FactureServiceImpl.class);
//        Mockito.doCallRealMethod().when(fMock).assignOperateurToFacture(Mockito.anyLong(), Mockito.anyLong());
//        factureService.assignOperateurToFacture(1L,1L);
//        Mockito.verify(fMock, Mockito.times(1)).assignOperateurToFacture(1L,1L);
//    }
    @Test
    @Order(5)
    void assignOperateurToFacture() {
        Facture f = new Facture((float) 7.4, (float) 99.2,new Date(),new Date(),false);
        Operateur o = new Operateur("elj", "aziz", "123");
        Facture factureAdded = factureService.addFacture(f);
        Operateur operateurAdded = iOperateurService.addOperateur(o);
        factureService.assignOperateurToFacture(operateurAdded.getIdOperateur(),factureAdded.getIdFacture());
        assertNotNull(iOperateurService.retrieveOperateur(operateurAdded.getIdOperateur()).getFactures());
        iOperateurService.deleteOperateur(operateurAdded.getIdOperateur());
        factureRepository.delete(factureAdded);
    }



//    @Test
//    @Order(6)
//    void pourcentageRecouvrement() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date1 = dateFormat.parse("2022-10-25",new ParsePosition(0));
//        Date date2 = dateFormat.parse("2022-10-26",new ParsePosition(0));
//        float p = factureService.pourcentageRecouvrement(date1,date2);
//        assertTrue(p>=0);
//    }
}