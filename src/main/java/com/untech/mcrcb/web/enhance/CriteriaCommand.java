package com.untech.mcrcb.web.enhance;

import org.hibernate.criterion.DetachedCriteria;

public abstract interface CriteriaCommand
{
    public static final String SORT_DESC = "desc";

    public static final String SORT_ASC  = "asc";

    public abstract DetachedCriteria execute(DetachedCriteria paramCriteria);
}