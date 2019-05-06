package com.untech.mcrcb.web.enhance;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
/**
 *  * @author lxt
 * @version 1.0
 * @since 2012-12-24
 */
public class FieldCommandImpl implements CriteriaCommand
{
    private Disjunction  expression = Restrictions.disjunction();

    private List<String> properties = new ArrayList<String>();

    private QueryFilter  filter;

    public FieldCommandImpl(String property, List<Object> value, String operation, QueryFilter filter)
    {
        if (value == null)
        {
            expression.add(restrictions(operation, property, null));
        }
        else
        {
            for (Object object : value)
            {
                expression.add(restrictions(operation, property, object));
            }
        }
        this.filter = filter;
    }

    public FieldCommandImpl(String property, Object value, String operation, QueryFilter filter)
    {
        expression.add(restrictions(operation, property, value));
        this.filter = filter;
    }

    private Criterion restrictions(String operation, String property, Object value)
    {
        properties.add(property);
        if ("LT".equals(operation))
        {
            return Restrictions.lt(property, value);
        }
        else
            if ("GT".equals(operation))
            {
                return Restrictions.gt(property, value);
            }
            else
                if ("LE".equals(operation))
                {
                    return Restrictions.le(property, value);
                }
                else
                    if ("GE".equals(operation))
                    {
                        return Restrictions.ge(property, value);
                    }
                    else
                        if ("LK".equals(operation))
                        {
                            return Restrictions.like(property, "%" + value + "%").ignoreCase();
                        }
                        else
                            if ("LFK".equals(operation))
                            {
                                return Restrictions.like(property, value + "%").ignoreCase();
                            }
                            else
                                if ("RHK".equals(operation))
                                {
                                    return Restrictions.like(property, "%" + value).ignoreCase();
                                }
                                else
                                    if ("NULL".equals(operation))
                                    {
                                        return Restrictions.isNull(property);
                                    }
                                    else
                                        if ("NOTNULL".equals(operation))
                                        {
                                            return Restrictions.isNotNull(property);
                                        }
                                        else
                                            if ("EMP".equals(operation))
                                            {
                                                return Restrictions.isEmpty(property);
                                            }
                                            else
                                                if ("NOTEMP".equals(operation))
                                                {
                                                    return Restrictions.isNotEmpty(property);
                                                }
                                                else
                                                    if ("IN".equals(operation))
                                                    {
                                                        return Restrictions.in(property, (Object[]) value);
                                                    }
                                                    else
                                                        if ("NOTIN".equals(operation))
                                                        {
                                                            return Restrictions.not(Restrictions.in(property,
                                                                    (Object[]) value));
                                                        }
                                                        else
                                                            if ("OR".equals(operation))
                                                            {
                                                                return Restrictions.not(Restrictions.in(property,
                                                                        (Object[]) value));

                                                            }
                                                            else
                                                                if ("NEQ".equals(operation))
                                                                {
                                                                    return Restrictions.ne(property, value);
                                                                }
                                                                else
                                                                {
                                                                    return Restrictions.eq(property, value);
                                                                }
    }

    public DetachedCriteria execute(DetachedCriteria criteria)
    {
        for (String property : properties)
        {
            String[] propertys = property.split("[.]");

            if ((propertys != null) && (propertys.length > 1) && (!("id".equals(propertys[0]))))
            {
                for (int i = 0; i < propertys.length - 1; ++i)
                {
                    if (!(filter.getAliasSet().contains(propertys[i])))
                    {
                        criteria.createAlias(propertys[i], propertys[i]);
                        filter.getAliasSet().add(propertys[i]);
                    }

                }
            }
        }
        criteria.add(expression);
        return criteria;
    }

    public void addOrCriteria(String operation, String property, Object value)
    {
        expression.add(restrictions(operation, property, value));
    }

    public void addAndCriteria(String operation, String property, Object value)
    {
        expression.add(restrictions(operation, property, value));
    }

    public void addOrCriteria(String operation, String property, List<Object> value)
    {
        for (Object object : value)
        {
            expression.add(restrictions(operation, property, object));
        }
    }

    public void addAndCriteria(String operation, String property, List<Object> value)
    {

        for (Object object : value)
        {
            expression.add(restrictions(operation, property, object));
        }
    }
}