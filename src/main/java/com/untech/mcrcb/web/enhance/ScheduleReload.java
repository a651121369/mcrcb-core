//-------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------

package com.untech.mcrcb.web.enhance;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;

import com.untech.mcrcb.web.util.Utils;
import com.unteck.common.dao.support.CustomSQL;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * Description of the class
 *
 * @author            lxt
 * @version           1.0
 * @since             2013-4-3
 */
@Component
public class ScheduleReload implements ServletContextAware, InitializingBean
{
    
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private ServletContext sc;
    
    public void execute() {
        getLasttjrq();
    }
    
    private void getLasttjrq() {
//        String rowSQL = CustomSQL.getInstance().get("sys_syslog_lasttjrq");
//        
//        Map<String, Object> result = jdbcTemplate.queryForMap(rowSQL);
//        if (result.get("tjrq") != null)
        if (sc != null)
            sc.setAttribute("tjrq", Utils.getPreDay(Utils.getCurrentDay()));//NumberUtils.toInt(DateFormatUtils.format(((Timestamp)result.get("tjrq")).getTime(), "yyyyMMdd")));
//        else 
//            sc.setAttribute("tjrq", NumberUtils.toInt(DateFormatUtils.format(new Date(), "yyyyMMdd")));
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        getLasttjrq();
    }

    @Override
    public void setServletContext(ServletContext sc)
    {
        this.sc = sc;
    }
}

