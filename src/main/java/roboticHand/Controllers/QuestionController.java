package roboticHand.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import roboticHand.DAO.ActionRepository;
import roboticHand.DAO.QuestionRepository;
import roboticHand.DAO.UserRepository;
import roboticHand.Model.Action;
import roboticHand.Model.Question;
import roboticHand.Model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping(value = "/")
    public String questionRedirect(HttpServletRequest request, Model model){
        String rights = (String) request.getSession().getAttribute("rights");
        String login = (String) request.getSession().getAttribute("login");

        if(rights.equals("S")) {
            questionRepository.getAllQuestions(request);
            return "questionA";
        } else if(rights.equals("A")) {
            questionRepository.getQuestionsForAdmin(request);
            return "questionA";
        }else {
            questionRepository.getUserQuestions(login, request);
            return "questionU";
        }
    }

    @PostMapping(value = "/answer")
    public ModelAndView answerQuestion(@RequestParam String userLogin, @RequestParam String userQ,
                                       @RequestParam String answer) {
        String status = "N";
        if(!answer.equals("No answer yet")){
            status = "A";
        }
        Question question = new Question();
        question.setQuestion(userQ);
        question.setUser(userLogin);

        questionRepository.updateAnswer(question, answer, status);
        return new ModelAndView("redirect:/question/");
    }

    @PostMapping(value = "/ask")
    public ModelAndView askQuestion(@RequestParam String newQuestion, HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");

        questionRepository.createQuestion(login, newQuestion);
        return new ModelAndView("redirect:/question/");
    }
}
