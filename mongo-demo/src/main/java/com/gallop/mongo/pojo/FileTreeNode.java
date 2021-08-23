package com.gallop.mongo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author gallop
 * date 2021-08-23 11:32
 * Description:
 * Modified By:
 */
@Data
@AllArgsConstructor
@Builder
public class FileTreeNode extends AbstractDocument{
    private boolean isFolder; //是否是目录
    private String ownId; //目录拥有者
    private String type; //文件类型
    private String value; //文件（目录）名称
    private String url;
    private Long size;
    private Integer readCount;
    private Integer downCount;
    private boolean open;
    private Date date;
    private ObjectId parentId; //上级id
    private List<FileTreeNode> children;
    private Integer depth; //递归深度，从0 开始

    public void appendChild(FileTreeNode fileTreeNode){
        if(children ==null){
            children = new ArrayList<>();
            children.add(fileTreeNode);
        }else {
            children.add(fileTreeNode);
        }
    }

    public void appendChildren(List<FileTreeNode> list){
        if(children ==null){
            children = new ArrayList<>();
            children.addAll(list);
        }else {
            children.addAll(list);
        }
    }
}
