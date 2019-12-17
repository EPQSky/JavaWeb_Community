package cn.net.epq.community.contorller;

import cn.net.epq.community.dto.CommentDTO;
import cn.net.epq.community.dto.PaginationDTO;
import cn.net.epq.community.dto.QuestionDTO;
import cn.net.epq.community.mapper.CommentMapper;
import cn.net.epq.community.model.Comment;
import cn.net.epq.community.model.Question;
import cn.net.epq.community.model.User;
import cn.net.epq.community.service.CommentService;
import cn.net.epq.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionContorller {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question")
    public String question(@RequestParam(name = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question", questionDTO);
        PaginationDTO pagination = commentService.list(id);
        model.addAttribute("pagination", pagination);
        return "question";
    }

    @PostMapping("/question")
    public String reply(@RequestParam(name = "content") String content,
                        @RequestParam(name = "id") Integer id,
                        HttpServletRequest request,
                        Model model){
        model.addAttribute("content", content);
        model.addAttribute("id", id);

        if (content == null || content == ""){
            model.addAttribute("error", "评论内容不能为空！");
            return "redirect:/question?id="+id;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error", "用户未登陆");
            return "redirect:/question?id="+id;
        }

        Comment comment = new Comment();
        comment.setParentId(id);
        comment.setType(1);
        comment.setCommentator(user.getId());
        comment.setContent(content);

        commentService.createOrUpdate(comment);

        return "redirect:/question?id="+id;
    }
}
