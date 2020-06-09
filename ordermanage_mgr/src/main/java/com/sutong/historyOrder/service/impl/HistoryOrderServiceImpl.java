package com.sutong.historyOrder.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.entity.AuditWorkOrderHistoryTable;
import com.sutong.bjstjh.entity.StatisticalInfo;
import com.sutong.bjstjh.util.CollectionUtils;
import com.sutong.historyOrder.mapper.HistoryOrderMapper;
import com.sutong.historyOrder.model.ParameterHistoryOrder;
import com.sutong.historyOrder.service.HistoryOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

@Service(timeout = 20000)
public class HistoryOrderServiceImpl implements HistoryOrderService {

    private static final Logger log = LoggerFactory.getLogger(HistoryOrderServiceImpl.class);


    @Autowired
    private HistoryOrderMapper historyOrderMapper;

    /**
     * 查询常规外查工单信息
     *
     * @param parameterHistoryOrder
     * @return
     */
    @Override
    public PageInfo<AuditWorkOrderHistoryTable> doFindOrder(ParameterHistoryOrder parameterHistoryOrder) {
        //Mybatis分页插件PageHelper
        PageHelper.startPage(parameterHistoryOrder.getPageIndex(), parameterHistoryOrder.getPageSize());

        List<AuditWorkOrderHistoryTable> auditWorkOrderHistoryTables = historyOrderMapper.doFindOrder(parameterHistoryOrder);
        PageInfo<AuditWorkOrderHistoryTable> pageInfo = new PageInfo<>(auditWorkOrderHistoryTables);
        return pageInfo;

    }

    /**
     * 创建常规外查工单表
     *
     * @param auditWorkOrderHistoryTable
     * @return
     */
    @Override
    public Integer doInsertOrder(AuditWorkOrderHistoryTable auditWorkOrderHistoryTable) {
        return historyOrderMapper.doInsertOrder(auditWorkOrderHistoryTable);
    }

    /**
     * 导入常规外查工单表
     *
     * @param date
     * @return
     */
    @Override
    @Transactional
    public void doInsertTable(List<AuditWorkOrderHistoryTable> date) {
        if (date != null && date.size() > 0) {
            AtomicInteger atomic = new AtomicInteger();
            //按照700拆分
            List<List<AuditWorkOrderHistoryTable>> splitList = CollectionUtils.splitList(date, 500);
            ConcurrentLinkedDeque<Thread> dequeThread = new ConcurrentLinkedDeque<Thread>();
            int processors = Runtime.getRuntime().availableProcessors();
            int openThread = splitList.size() < processors ? splitList.size() : processors;
            //先将全部任务分配给对应的线程
            for (int t = 0; atomic.get() < splitList.size(); atomic.set(++t)) {
                dequeThread.offer(new Thread(() -> {
                    historyOrderMapper.doInsertTable(splitList.get(atomic.decrementAndGet()));
                }, "当前历史工单插入数据的线程：" + t + "-->"));
            }
            //如果开启的线程数刚好可以全部执行需要插入的次数
            if (openThread == splitList.size()) {
                dequeThread.forEach((o) -> o.start());
                dequeThread.forEach((o) -> {
                    try {
                        o.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                //分批次执行才能完成的情况,控制同时开启的线程数量,避免过度占用资源
//        		获取线程的最大执行次数
//        		int ThreadCount = (splitList.size()+openThread-1)/openThread;
                List<Thread> threadList = new ArrayList<Thread>();
                for (int i = 0; i < splitList.size(); i++) {
                    //判断是否为最后一个元素
                    boolean dequeSize = dequeThread.size() == 1;
                    //最大同时执行的线程不会超过openThread
                    if (i % openThread == 0 || dequeSize) {
                        if (dequeSize)
                            threadList.add(dequeThread.pop());
                        threadList.forEach((o) -> o.start());
                        threadList.forEach((o) -> {
                            try {
                                o.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        threadList.clear();
                    }
                    if (!dequeSize)
                        threadList.add(dequeThread.pop());
                }
            }
        }
    }

    /**
     * @description: 查询常规外查工单详情集合
     * @Param AuditWorkOrderHistoryTable: 常规外查工单实体
     * @return: AuditWorkOrderHistoryTable
     **/
    @Override
    public AuditWorkOrderHistoryTable doFindHistoryResultInfoSerialId(String serialId) {

        AuditWorkOrderHistoryTable auditWorkOrderHistoryTable = historyOrderMapper.doFindHistoryResultInfoSerialId(serialId);
        //补费金额转成元为单位
//        auditWorkOrderHistoryTable.setSumPay(auditWorkOrderHistoryTable.getSumPay() / 100);

        return auditWorkOrderHistoryTable;
    }

    /**
     * 修改常规外查补费状态
     *
     * @param hashMap
     * @return
     */
    @Override
    public Integer doUpdate1(Map hashMap) {
        return historyOrderMapper.doUpdate1(hashMap);
    }

    /**
     * 修改常规外查变更状态
     *
     * @param hashMap
     * @return
     */
    @Override
    public Integer doUpdate2(Map hashMap) {

        return historyOrderMapper.doUpdate2(hashMap);

    }

    /**
     * @param parameterHistoryOrder
     * @description: 查询追缴常规外查工单
     * @return: ParameterHistoryBuffer
     */
    @Override
    public PageInfo<AuditWorkOrderHistoryTable> doFindHistoryRecovered(ParameterHistoryOrder parameterHistoryOrder) {
        //Mybatis分页插件PageHelper
        PageHelper.startPage(parameterHistoryOrder.getPageIndex(), parameterHistoryOrder.getPageSize());

        List<AuditWorkOrderHistoryTable> historyRecovered = historyOrderMapper.doFindHistoryRecovered(parameterHistoryOrder);
       /* for (AuditWorkOrderHistoryTable table : historyRecovered) {
            String dictName = DictCache.queryName(DictEnum.VEHPLATE_COLORCODE, table.getVehplateColorCode());
            table.setVehplateNo(table.getVehplateNo() + "_" + dictName);
        }
*/
        PageInfo<AuditWorkOrderHistoryTable> pageInfo = new PageInfo<>(historyRecovered);
        return pageInfo;
    }

    /**
     * 常规外查工单责任归属统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> doFindHistoryTotal() {
        Map<String, Integer> historyTotalMap = new HashMap<String, Integer>();
        List<StatisticalInfo> total = historyOrderMapper.doFindHistoryTotal();
        //总数			=0	A
        //银行责任 		=1	B
        //业务员责任		=2	C
        //系统责任		=3	D
        //客户责任		=4	E
        //其他			=5	F
        char A = 65;
        for (StatisticalInfo stats : total) {
            Integer countnum = stats.getCountnum();

            historyTotalMap.put(stats.getDictNumber() + "", countnum == null ? 0 : countnum);

            historyTotalMap.put(stats.getDictNumber() + "", countnum == null ? 0 : countnum);

            String str = String.valueOf(A++);
            historyTotalMap.put(str, countnum == null ? 0 : countnum);

        }
        return historyTotalMap;
    }

    @Override
    public List<AuditWorkOrderHistoryTable> doFindBatchById(List<Object> id) {
        List<AuditWorkOrderHistoryTable> selectBatchById = historyOrderMapper.doFindBatchById(id);
        return selectBatchById;
    }
	@Override
	public List<AuditWorkOrderHistoryTable> doFindBatchByObuId(List<Object> id) {
		 List<AuditWorkOrderHistoryTable> selectBatchById = historyOrderMapper.doFindBatchByObuId(id);
	     return selectBatchById;
	}
}
