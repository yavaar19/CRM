package com.yavaar.nosi.crm.unit;

import com.yavaar.nosi.crm.dao.CustomerDao;
import com.yavaar.nosi.crm.entity.Address;
import com.yavaar.nosi.crm.entity.Customer;
import com.yavaar.nosi.crm.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class CustomerControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Value("${sql.script.delete.customer}")
    private String SQLDELETECUSTOMER;

    @Value("${sql.script.delete.address}")
    private String SQLDELETEADDRESS;

    @Value("${sql.script.delete.customer_address}")
    private String SQLDELETECUSTOMERADDRESS;

    @BeforeAll
    static void setUp() {

        request = new MockHttpServletRequest();
        request.setParameter("firstName", "Johny");
        request.setParameter("lastName", "Sparrow");
        request.setParameter("email", "johny@gmail.com");
        request.setParameter("dob", "1912-11-11");
        request.setParameter("streetNumber", "106");
        request.setParameter("streetName", "Park Lane");
        request.setParameter("city", "Bradford");
        request.setParameter("province", "ON");
        request.setParameter("postalCode", "T4U-2N5");

    }

    @BeforeEach
    void beforeEach() {

        System.out.println("YOLOOOO");

        jdbc.execute("INSERT INTO CUSTOMER(ID, FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH) "
        + "VALUES ('3', 'Lary', 'Larson', 'lary@gmail.com', '1912-11-11')");

        jdbc.execute("INSERT INTO ADDRESS(ID, STREET_NUMBER, STREET_NAME, CITY, PROVINCE, POSTAL_CODE) "
                + "VALUES ('2', 342, 'Winton Street', 'Belville', 'ON', 'J7F-6G5')");
        jdbc.execute("INSERT INTO CUSTOMER_ADDRESS(CUSTOMER_ID, ADDRESS_ID) "
                + "VALUES (3, 2)");

    }

    @AfterEach
    void tearDown() {

        jdbc.execute(SQLDELETECUSTOMERADDRESS);
        jdbc.execute(SQLDELETECUSTOMER);
        jdbc.execute(SQLDELETEADDRESS);

    }

    @Test
    void canGetCustomersWithAddressHttpRequest() throws Exception {

        Address address1 = new Address(58, "Luka Road", "Bradford", "ON", "L4R-S5G");
        Address address2 = new Address(58, "George Street", "Deliote", "WA", "U4V-K5G");
        Customer customer1 = new Customer("John", "Doe","john@gmail.com" , LocalDate.of(1990, 10, 17));
        Customer customer2 = new Customer("Emily", "Tanner","emily@gmail.com" , LocalDate.of(1985, 9, 4));

        customer1.addAddress(address1);
        customer2.addAddress(address2);

        List<Customer> customerList = new ArrayList<>(Arrays.asList(customer1, customer2));

        when(customerService.findAllCustomersJoinFetchAddress()).thenReturn(customerList);

        assertIterableEquals(customerList, customerService.findAllCustomersJoinFetchAddress());

        MvcResult mvcResult = mockMvc.perform(get("/customers"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "customers");

    }

//    @Test
//    void canCreateCustomerHttpRequest() throws Exception {
//
//        Customer customer1 = new Customer("Jack", "Dawson", "jack@gmail.com", LocalDate.of(1988, 03, 05));
//
//        List<Customer> customerList = new ArrayList<>(Arrays.asList(customer1));
//
//        when(customerService.findAllCustomers()).thenReturn(customerList);
//
//        assertIterableEquals(customerList, customerService.findAllCustomers());
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/customers").contentType(MediaType.APPLICATION_JSON)
//                .param("firstName", request.getParameter("firstName"))
//                .param("lastName", request.getParameter("lastName"))
//                .param("email", request.getParameter("email"))
//                        .param("streetNumber", request.getParameter("streetNumber"))
//                        .param("streetName", request.getParameter("streetName"))
//                        .param("city", request.getParameter("city"))
//                        .param("province", request.getParameter("province"))
//                        .param("postalCode", request.getParameter("postalCode")))
//                .andExpect(status().is3xxRedirection()).andReturn();
//
//        ModelAndView mav = mvcResult.getModelAndView();
//
//        assertViewName(mav, "redirect:customers");
//
//        Customer customer = customerDao.findCustomerByEmail("johny@gmail.com").get();
//
//        assertNotNull(customer);
//
//    }
//
//    @Test
//    void canDeleteCustomerHttpRequest() throws Exception {
//
//        assertTrue(customerDao.findById(3L).isPresent());
//
//        MvcResult mvcResult = mockMvc.perform(get("/delete/customer/{id}", 3L))
//                .andExpect(status().isOk()).andReturn();
//
//        ModelAndView mav = mvcResult.getModelAndView();
//
//        assertViewName(mav, "customers");
//
//        assertFalse(customerDao.findById(3L).isPresent());
//
//    }
//
//    @Test
//    void canGetCustomerAndAddressesHttpRequest() throws Exception {
//
//        assertNotNull(customerDao.findCustomerByIdJoinFetchAddress(3L).get());
//
//        MvcResult mvcResult = mockMvc.perform(get("/customer/profile/{id}", 3))
//                .andExpect(status().isOk()).andReturn();
//
//        ModelAndView mav = mvcResult.getModelAndView();
//
//        assertViewName(mav, "customer-profile");
//
//    }
//
//    @Test
//    void canGetCustomerAndOrdersHttpRequest() throws Exception {
//
//        Customer customer = customerDao.findById(3L).get();
//        Order order = new Order(LocalDate.of(2023, 12,11), new BigDecimal("200.00"),
//                new BigDecimal("40.00"), new BigDecimal("240.00"), PaymentType.AMERICAN_EXPRESS);
//
//        order.setCustomer(customer);
//
//        customerDao.save(customer);
//
//        assertNotNull(customerDao.findCustomerByIdJoinFetchOrder(3L));
//
//        MvcResult mvcResult = mockMvc.perform(get("/customer/order/{id}", 3))
//                .andExpect(status().isOk()).andReturn();
//
//        ModelAndView mav = mvcResult.getModelAndView();
//
//        assertViewName(mav, "customer-orders");
//
//    }

}
