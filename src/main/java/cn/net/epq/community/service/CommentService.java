package cn.net.epq.community.service;

import cn.net.epq.community.dto.CommentDTO;
import cn.net.epq.community.dto.PaginationDTO;
import cn.net.epq.community.mapper.CommentMapper;
import cn.net.epq.community.mapper.UserMapper;
import cn.net.epq.community.model.Comment;
import cn.net.epq.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer id) {
        PaginationDTO paginationDTO = new PaginationDTO();
        List<Comment> comments = commentMapper.list(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : comments){
            User user = userMapper.findById(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }
        paginationDTO.setComments(commentDTOList);
        return paginationDTO;
    }
}
