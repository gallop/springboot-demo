package com.gallop.mongo;

import com.gallop.mongo.dao.FileTreeNodeDao;
import com.gallop.mongo.pojo.FileTreeNode;
import com.gallop.mongo.util.JsonUtils;
import com.gallop.mongo.util.TreeUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest(classes = MongoDemoApplication.class)
class MongoDemoApplicationTests {

    @Autowired
    private FileTreeNodeDao treeNodeDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void saveTreeNode(){
        FileTreeNode treeNode1 = FileTreeNode.builder()
                .isFolder(true)
                .ownId("ab001")
                .type("folder")
                .value("高中代数")
                .size(0l)
                .readCount(0)
                .downCount(0)
                .parentId(new ObjectId("6123594965ef014f4b3f1284"))
                .open(true)
                .date(new Date())
                .build();

        treeNodeDao.saveTreeNode(treeNode1);
    }

     /**
      * @date 2021-08-23 16:24
      * Description: 根据根节点(可以有多个节点)，获取此节点下的整个树结构
      * Param:
      * return:
      **/
    @Test
    public void getTreeNodeList(){
        String id = "61231c4b559d6043d620189e";
        String id2 = "6123594965ef014f4b3f1284";
        List<FileTreeNode> list = treeNodeDao.getTreeNodeOfAllChildren(id,id2);
        System.err.println("list:"+list.toString());

        list.stream().forEach(item->{
            if(item.getChildren() != null && item.getChildren().size()>0){
                System.err.println("children-size="+item.getChildren().size());
                item.getChildren().forEach(e->{
                    System.out.println(""+e.toString());
                });
                //map = sortByKey(map);

                List<FileTreeNode> childrenNode = TreeUtil.buildTreeNode(item.getChildren());
                item.getChildren().clear();
                item.appendChildren(childrenNode);
                //System.err.println("item:"+ JsonUtils.objectToJson(item));
            }
        });
        System.err.println("list:"+ JsonUtils.objectToJson(list));


    }

    private Map<Integer, List<FileTreeNode>> sortByKey(Map<Integer, List<FileTreeNode>> map) {
        Map<Integer, List<FileTreeNode>> result = new LinkedHashMap<>(map.size());
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }



}
