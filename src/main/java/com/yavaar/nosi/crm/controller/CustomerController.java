package com.yavaar.nosi.crm.controller;

import com.yavaar.nosi.crm.entity.Address;
import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String getCustomers(Model m) {

        List<Customer> customerList = customerService.findAllCustomersJoinFetchAddress();
        m.addAttribute("customers", customerList);

        return "customers";

    }

    @PostMapping("/customers")
    public String createCustomer(@ModelAttribute("customer") Customer customer, @ModelAttribute("address") Address address, Model m) {

        // NEED TO IMPLEMENT OBJECT NULL AND ERROR CHECKS LATER ON

        customer.addAddress(address);

        customerService.saveCustomer(customer);

        List<Customer> customerList = customerService.findAllCustomersJoinFetchAddress();
        m.addAttribute("customers", customerList);

        return "redirect:customers";

    }

    @GetMapping("/customer/profile/{id}")
    public String customerProfile(@PathVariable long id, Model m) {

        // NEED TO IMPLEMENT CUSTOMER AND ORDER NOT FOUND EXCEPTION

        Customer customer = customerService.findCustomerByIdJoinFetchAddress(id).get();

        m.addAttribute("customerprofile", customer);

        return "customer-profile";

    }

    @GetMapping("/customer/order/{id}")
    public String customerOrders(@PathVariable long id, Model m) {

        // NEED TO IMPLEMENT CUSTOMER AND ORDER NOT FOUND EXCEPTION

        Customer customer = customerService.findCustomerByIdJoinFetchOrder(id).get();

        m.addAttribute("customerorders", customer);

        return "customer-orders";

    }

    @GetMapping("/delete/customer/{id}")
    public String deleteStudent(@PathVariable long id, Model m) {

        // NEED TO IMPLEMENT CHECK ON IF CUSTOMER EXIST
        // NEED TO IMPLEMENT CHECK ON IF CUSTOMER HAS ORDERS

        customerService.deleteCustomerById(id);

        List<Customer> customerList = customerService.findAllCustomersJoinFetchAddress();

        m.addAttribute("customers", customerList);

        return "customers";

    }

}
