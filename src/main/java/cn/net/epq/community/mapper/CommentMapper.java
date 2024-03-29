package cn.net.epq.community.mapper;

import cn.net.epq.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id, type, commentator, gmt_create, gmt_modified, content) values (#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified}, #{content})")
    void create(Comment comment);

    @Select("select * from comment where parent_id = #{parentId} order by gmt_modified desc")
    List<Comment> list(Integer parent_id);
}
