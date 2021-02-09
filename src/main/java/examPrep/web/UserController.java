package examPrep.web;

import examPrep.model.binding.UserLoginBindingModel;
import examPrep.model.binding.UserRegiserBindingModel;
import examPrep.model.service.UserServiceModel;
import examPrep.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("userLoginBindingModel")){
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());

        }

        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid @ModelAttribute("userLoginBindingModel") UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes,
                               HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:login";
        }

        UserServiceModel user = this.userService.findByUsername(userLoginBindingModel.getUsername());

        if (user == null || !user.getPassword().equals(userLoginBindingModel.getPassword())){
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("notFound", true);
            return "redirect:login";

        }

        httpSession.setAttribute("user", user);

        return "redirect:/";

    }


    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegiserBindingModel")){
            model.addAttribute("userRegiserBindingModel", new UserRegiserBindingModel());

        }

        return "register";
    }


    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("userRegiserBindingModel")
                                          UserRegiserBindingModel userRegiserBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegiserBindingModel.getPassword()
                .equals(userRegiserBindingModel.getConfirmPassword())) {

            // TODO redirect attr;
            redirectAttributes.addFlashAttribute("userRegiserBindingModel", userRegiserBindingModel); // validation
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegiserBindingModel", bindingResult);
            return "redirect:register";
        }

        this.userService.register(this.modelMapper.map(userRegiserBindingModel, UserServiceModel.class));

        return "redirect:login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";
    }
}
