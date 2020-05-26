package com.mboaeat.order.domain;

import com.mboaeat.common.AbstractPeriodicalCollection;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

import static com.mboaeat.common.CollectionsUtils.newArrayList;

@Embeddable
public class MenuStatusLinkCollection extends AbstractPeriodicalCollection<MenuStatusLink, PeriodByDay> {

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
