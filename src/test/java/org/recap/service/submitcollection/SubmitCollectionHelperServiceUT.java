package org.recap.service.submitcollection;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.service.common.RepositoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;


public class SubmitCollectionHelperServiceUT extends BaseTestCaseUT {

    @InjectMocks
    SubmitCollectionHelperService submitCollectionHelperService;

    @Mock
    private RepositoryService repositoryService;

    @Mock
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Test
    public void getBibliographicEntityIfExist(){
        String owningInstitutionBibId = "12345";
        int owningInstitutionId = 12345;
        BibliographicEntity bibliographicEntityList = getBibliographicEntity();
        Mockito.when(repositoryService.getBibliographicDetailsRepository()).thenReturn(bibliographicDetailsRepository);
        Mockito.when(repositoryService.getBibliographicDetailsRepository().findByOwningInstitutionIdAndOwningInstitutionBibId(owningInstitutionId,owningInstitutionBibId)).thenReturn(bibliographicEntityList);
        BibliographicEntity bibliographicEntity = submitCollectionHelperService.getBibliographicEntityIfExist(owningInstitutionBibId,owningInstitutionId);
        assertNotNull(bibliographicEntity);
    }

    @Test
    public void attachItemToExistingBib(){
        List<ItemEntity> itemEntities = new ArrayList<>();
        itemEntities.add(getItemEntity());
        List<HoldingsEntity> holdingsEntities = new ArrayList<>();
        holdingsEntities.add(getHoldingsEntity());
        BibliographicEntity existingBibliographicEntity = getBibliographicEntity();
        existingBibliographicEntity.setItemEntities(itemEntities);
        existingBibliographicEntity.setHoldingsEntities(holdingsEntities);
        BibliographicEntity incomingBibliographicEntity = getBibliographicEntity();
        HoldingsEntity holdingsEntity = getHoldingsEntity();
        holdingsEntity.setOwningInstitutionHoldingsId("234");
        List<HoldingsEntity> holdingsEntities1 = new ArrayList<>();
        holdingsEntities1.add(holdingsEntity);
        incomingBibliographicEntity.setItemEntities(itemEntities);
        incomingBibliographicEntity.setHoldingsEntities(holdingsEntities1);
        submitCollectionHelperService.attachItemToExistingBib(existingBibliographicEntity,incomingBibliographicEntity);
        assertNotNull(existingBibliographicEntity);
    }

    @Test
    public void attachItemToExistingBibHoldingExist(){
        List<ItemEntity> itemEntities = new ArrayList<>();
        itemEntities.add(getItemEntity());
        List<HoldingsEntity> holdingsEntities = new ArrayList<>();
        holdingsEntities.add(getHoldingsEntity());
        BibliographicEntity existingBibliographicEntity = getBibliographicEntity();
        existingBibliographicEntity.setItemEntities(itemEntities);
        existingBibliographicEntity.setHoldingsEntities(holdingsEntities);
        HoldingsEntity holdingsEntity = getHoldingsEntity();
        holdingsEntity.setOwningInstitutionHoldingsId("234");
        submitCollectionHelperService.attachItemToExistingBib(existingBibliographicEntity,existingBibliographicEntity);
        assertNotNull(existingBibliographicEntity);
    }

    @Test
    public void getHoldingItemIdMap(){
        List<ItemEntity> itemEntities = new ArrayList<>();
        itemEntities.add(getItemEntity());
        HoldingsEntity holdingsEntity = getHoldingsEntity();
        holdingsEntity.setItemEntities(itemEntities);
        holdingsEntity.setOwningInstitutionHoldingsId("234");
        BibliographicEntity existingBibliographicEntity = getBibliographicEntity();
        existingBibliographicEntity.setItemEntities(itemEntities);
        existingBibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        Map<String,Map<String,ItemEntity>> holdingItemMap=  submitCollectionHelperService.getHoldingItemIdMap(existingBibliographicEntity);
        assertNotNull(holdingItemMap);
    }

    private BibliographicEntity getBibliographicEntity(){
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setId(123456);
        bibliographicEntity.setContent("Test".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setCreatedBy("tst");
        bibliographicEntity.setLastUpdatedBy("tst");
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId("1577261074");
        bibliographicEntity.setDeleted(false);
        HoldingsEntity holdingsEntity = getHoldingsEntity();
        ItemEntity itemEntity = getItemEntity();
        itemEntity.setBibliographicEntities(Arrays.asList(bibliographicEntity));
        holdingsEntity.setItemEntities(Arrays.asList(itemEntity));
        return bibliographicEntity;
    }

    private HoldingsEntity getHoldingsEntity() {
        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setCreatedBy("tst");
        holdingsEntity.setLastUpdatedBy("tst");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId("12345");
        holdingsEntity.setDeleted(false);
        return holdingsEntity;
    }

    private ItemEntity getItemEntity(){
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setOwningInstitutionItemId("843617540");
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setBarcode("123456");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("123");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setCatalogingStatus("Complete");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setDeleted(false);
        return itemEntity;
    }

}

