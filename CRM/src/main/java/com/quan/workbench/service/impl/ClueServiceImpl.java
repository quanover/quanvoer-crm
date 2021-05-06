package com.quan.workbench.service.impl;

import com.quan.util.DateTimeUtil;
import com.quan.util.FlagUtil;
import com.quan.util.UUIDUtil;
import com.quan.vo.PageVO;
import com.quan.workbench.dao.*;
import com.quan.workbench.domain.*;
import com.quan.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private ClueDao clueDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;

    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    //客户 公司
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;

    //客服联系人
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;

    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;


    @Override
    public boolean saveClue(Clue clue) {

        int num=clueDao.saveClue(clue);

        return FlagUtil.flag(num);
    }

    @Override
    public PageVO<Clue> pageList(Map<String, Object> map) {
        PageVO<Clue> vo=new PageVO<>();
        int total=clueDao.getTotalByLike(map);
        List<Clue> dataList=clueDao.pageListByLike(map);

        vo.setDataList(dataList);
        vo.setTotal(total);

        return vo;
    }

    @Override
    public Clue detail(String id) {


        return clueDao.detailById(id);
    }

    @Override
    public Boolean disassociate(String id) {
        int num=clueActivityRelationDao.deleteById(id);
        return FlagUtil.flag(num);
    }

    @Override
    public boolean relate(String clueId, String[] activityId) {
        boolean flag=true;
        ClueActivityRelation clueActivityRelation=new ClueActivityRelation();
        for (String aid : activityId) {
            String id=UUIDUtil.getUUID();
            clueActivityRelation.setId(id);
            clueActivityRelation.setActivityId(aid);
            clueActivityRelation.setClueId(clueId);

            int num=clueActivityRelationDao.relate(clueActivityRelation);
            if(num!=1){
                flag=false;
            }
        }



        return flag;
    }

    @Override
    public boolean change(String clueId, Tran tran, String createBy) {
        String createTime= DateTimeUtil.getSysTime();
        boolean flag=true;

        //(1) 获取到线索id，通过线索id获取线索对象
        Clue clue=clueDao.getById(clueId);
        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户
        String company=clue.getCompany();
        Customer customer=customerDao.getByName(company);

        if(customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setContactSummary(clue.getContactSummary());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setDescription(clue.getDescription());
            customer.setName(company);
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setPhone(clue.getPhone());
            customer.setWebsite(clue.getWebsite());
            customer.setOwner(clue.getOwner());

           int num1=customerDao.save(customer);
           if(num1!=1){
               flag=false;
           }
        }

        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts=new Contacts();
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setCreateTime(createTime);
        contacts.setCreateBy(createBy);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());

        int num2=contactsDao.save(contacts);
        if(num2!=1){
            flag=false;
        }

        //(4) 线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList=clueRemarkDao.getByCId(clueId);

        for (ClueRemark clueRemark : clueRemarkList) {
            String noteContent = clueRemark.getNoteContent();
            //客户
            CustomerRemark customerRemark=new CustomerRemark();
            customerRemark.setNoteContent(noteContent);
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setCreateTime(createTime);
            customerRemark.setCreateBy(createBy);
            int num3=customerRemarkDao.save(customerRemark);
            if(num3!=1){
                flag=false;
            }
            //联系人
            ContactsRemark contactsRemark=new ContactsRemark();
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setCreateBy(createBy);
            int num4=contactsRemarkDao.save(contactsRemark);
            if(num4!=1){
                flag=false;
            }
        }
        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<String> activityIdList=clueActivityRelationDao.getByClueId(clueId);
        for (String activityId : activityIdList) {
            ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());
            int num5=contactsActivityRelationDao.save(contactsActivityRelation);
            if(num5!=1){
                flag=false;
            }
        }
        //(6) 如果有创建交易需求，创建一条交易
        if(tran!=null){
                /*
                id,createBy,CreateTime,money,name,expectedDate,stage,activityId
                 */
            tran.setOwner(clue.getOwner());
            tran.setCustomerId(customer.getId());
            tran.setSource(clue.getSource());
            tran.setContactsId(contacts.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setDescription(clue.getDescription());
            tran.setNextContactTime(clue.getNextContactTime());
            int num6=tranDao.save(tran);
            if(num6!=1){
                flag=false;
            }

            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory=new TranHistory();
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setStage(tran.getStage());
            tranHistory.setTranId(tran.getId());
            int num7=tranHistoryDao.save(tranHistory);
            if(num7!=1){
                flag=false;
            }
        }

        //(8) 删除线索备注
        for (ClueRemark clueRemark : clueRemarkList){
            int num8=clueRemarkDao.delete(clueRemark);
            if(num8!=1){
                flag=false;
            }
        }

        //(9) 删除线索和市场活动的关系
        for (String activityId : activityIdList){
            int num9=clueActivityRelationDao.deleteByAcId(activityId);
            if(num9!=1){
                flag=false;
            }
        }

        //(10) 删除线索
        int num10=clueDao.deleteById(clueId);
        if(num10!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {
        Map<String,Object> map=new HashMap<>();
        //总
        int total=clueDao.getTotal();
        //list
        List<Map<String,Object>> list=clueDao.getCharts();
        map.put("total",total);
        map.put("list",list);


        return map;
    }


}
