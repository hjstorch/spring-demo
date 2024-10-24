package de.sopracss.demo.batch.tax;


import de.sopracss.demo.persistence.entity.TaxEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class TaxProcessor implements ItemProcessor<TaxEntity, TaxEntity> {

    @Override
    public TaxEntity process(TaxEntity item) throws Exception {

        if(item.getLocale().toUpperCase().contains("DE")){
            item.setRateNormal(BigInteger.valueOf(21));
        }

        return item;
    }
}
