package com.mboaeat.order.domain;

import com.mboaeat.order.domain.menu.MenuCategory;
import com.mboaeat.order.domain.menu.MenuPhoto;
import com.mboaeat.order.domain.product.Product;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedNameParser;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class IdentifierGenerator extends SequenceStyleGenerator {

    private final static Map<String, String> ENTITY_SEQUENCES_NAMES;

    static {
        ENTITY_SEQUENCES_NAMES = new HashMap<>();
        ENTITY_SEQUENCES_NAMES.put(Product.class.getName(), "SEQ_PRODUCTS");
        ENTITY_SEQUENCES_NAMES.put(Menu.class.getName(), "SEQ_MENUS");
        ENTITY_SEQUENCES_NAMES.put(Order.class.getName(), "SEQ_ORDERS");
        ENTITY_SEQUENCES_NAMES.put(OrderLine.class.getName(), "SEQ_ORDER_LINES");
        ENTITY_SEQUENCES_NAMES.put(PaymentMode.class.getName(), "SEQ_PAYMENTS_MODE");
        ENTITY_SEQUENCES_NAMES.put(MenuCategory.class.getName(), "SEQ_MENU_CATEGORY");
        ENTITY_SEQUENCES_NAMES.put(MenuPhoto.class.getName(), "SEQ_MENU_PHOTO");
    }

    @Override
    protected QualifiedName determineSequenceName(Properties params, Dialect dialect, JdbcEnvironment jdbcEnv, ServiceRegistry serviceRegistry) {

        QualifiedName qualifiedName = super.determineSequenceName(params, dialect, jdbcEnv, serviceRegistry);


        final String entityName = ConfigurationHelper.getString( ENTITY_NAME, params, null );

        if( entityName != null && ENTITY_SEQUENCES_NAMES.get(entityName) != null){
            return new QualifiedNameParser.NameParts(
                    qualifiedName.getCatalogName(),
                    qualifiedName.getSchemaName(),
                    jdbcEnv.getIdentifierHelper().toIdentifier( ENTITY_SEQUENCES_NAMES.get(entityName) )
            );
        }

        return qualifiedName;
    }
}
