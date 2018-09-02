package esform.espage;

import esform.dao.PageDao;
import esform.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2018/9/2.
 */
@Service
public class PageServiceImpl {

    private static final String VALUE = "local";

    @Autowired
    private PageDao pageDao;

    public Page get(Page example){
        List<Page> pages = pageDao.selectByExample(example);
        if(pages.size()>0){
            return pages.get(0);
        }
        return null;
    }

    public int update(Page page){
        return pageDao.update(page);
    }

    @CachePut(value = VALUE, key = "'key_' + #page.id")
    public Page putLocalCache(Page page){
        return page;
    }

    @Cacheable(value = VALUE, key = "'key_' + #example.id")
    public Page getLocalCache(Page example){
        return null;
    }
}
