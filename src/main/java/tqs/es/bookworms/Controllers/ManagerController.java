package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.es.bookworms.DB.ManagerRepository;
import tqs.es.bookworms.Entities.Location;
import tqs.es.bookworms.Entities.Manager;
import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

@RequestMapping(value = "/bookworms")
@Controller
public class ManagerController {

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    BookController bookController;

    @Autowired
    LocationController locationController;

    //SIGNUP
    @GetMapping(value="/signup/manager")
    public String signUp(){
        return "manager/SignUpManager_form";
    }

    //REGISTO
    @PostMapping(value="/signup/manager")
    public String registration(@ModelAttribute Manager manager, Model model) {
        try {
            if (manager.getName().isEmpty() || manager.getPassword().isEmpty() || manager.getEmail().isEmpty()) {
                return "manager/SignUpManagerError_form";
            }
            managerRepository.save(manager);
            //homepage vai ter os 3 formulários de login para os seus users
            return "redirect:/bookworms/";
        } catch (DataIntegrityViolationException e) {
            return "manager/SignUpManagerError_form";
        }
    }

    //LOGIN - faz redirect para a MainPage if successful
    @PostMapping(value = "/login/manager")
    public String Login(@ModelAttribute Manager manager){
        return null;
    }

    //MAINPAGE-MANAGER
    @GetMapping(value = "/manager/{managerId}")
    public String MainPage(@PathVariable("managerId") Long managerId, Model model){
        Manager manager = managerRepository.getById(managerId);
        model.addAttribute("manager", manager);
        return "manager/MainPage_form";
    }

    //CLICKING BUTTON THAT TAKES TO FORM TO ADD LOCATION
    @GetMapping(value = "/manager/{managerId}/addLocationForm")
    public String addLocation(@PathVariable("managerId") Long managerId, Model model){
        model.addAttribute("location", new Location());
        return "manager/AddLocationForm_form";
    }

    @PostMapping(value="/manager/{managerId}/addLocationForm")
    public String addLocation(@PathVariable("managerId") Long managerId, @ModelAttribute Location location) throws ResourceNotFoundException {

        locationController.addLocation(location);

        String redirect = String.format("redirect:/bookworms/manager/%x", managerId);
        return redirect;
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //MAPPINGS AQUI SÃO SÓ PARA CONFIRMAÇÃO NO POSTMAN QUE ESTÁ TUDO BEM

}
