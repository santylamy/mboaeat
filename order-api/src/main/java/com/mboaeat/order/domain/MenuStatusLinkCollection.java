package com.mboaeat.order.domain;

import com.mboaeat.domain.AbstractPeriodicalCollection;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@Embeddable
public class MenuStatusLinkCollection extends AbstractPeriodicalCollection<MenuStatusLink, PeriodByDay> implements Serializable {

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    private List<MenuStatusLink> menuStatusLinks = newArrayList();

    @Override
    protected List<MenuStatusLink> getPeriodicals() {
        return menuStatusLinks;
    }

    @Override
    public AbstractPeriodicalCollection<MenuStatusLink, PeriodByDay> copy() {
        MenuStatusLinkCollection statusLinkCollection = new MenuStatusLinkCollection();
        statusLinkCollection.menuStatusLinks = menuStatusLinks;
        return statusLinkCollection;
    }

}
