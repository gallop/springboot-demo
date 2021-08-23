package com.gallop.mongo.dao;

import com.gallop.mongo.pojo.FileTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * author gallop
 * date 2021-08-23 11:41
 * Description:
 * Modified By:
 */
@Component
public class FileTreeNodeDao {
    @Autowired
    private MongoTemplate mongoTemplate;


    public void saveTreeNode(FileTreeNode treeNode) {
        this.mongoTemplate.save(treeNode);
    }
     /**2
      * date 2021-08-23 16:48
      * Description: 对应的数据库查询脚本语句如下：
      * db.fileTreeNode.aggregate( [
      *    { $match: {
      *         _id: ObjectId("61231c4b559d6043d620189e")
      *     }},
      *    {
      *       $graphLookup: {
      *          from: "fileTreeNode",
      *          startWith: "$_id",
      *          connectFromField: "_id",
      *          connectToField: "parentId",
      *          depthField: "depth",
      *          maxDepth: 0,
      *          as: "children"
      *       }
      *    }
      * ] )
      **/
    public List<FileTreeNode> getTreeNodeOfAllChildren(String ...ids){
        Aggregation agg = newAggregation(
                match(Criteria.where("_id").in(ids)), //筛选符合条件的记录
                graphLookup("fileTreeNode")
                        .startWith("$_id")
                        .connectFrom("_id")
                        .connectTo("parentId")
                        .depthField("depth")
                        .as("children")
        );
        AggregationResults<FileTreeNode> results = mongoTemplate.aggregate(agg, "fileTreeNode", FileTreeNode.class);
        List<FileTreeNode> fileTreeNodeList = results.getMappedResults();

        return fileTreeNodeList;
    }
}
