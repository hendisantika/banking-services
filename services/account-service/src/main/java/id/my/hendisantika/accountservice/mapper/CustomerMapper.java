package id.my.hendisantika.accountservice.mapper;

import id.my.hendisantika.accountservice.dto.CustomerDetailsDto;
import id.my.hendisantika.accountservice.dto.CustomerDto;
import id.my.hendisantika.accountservice.entity.Customer;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.38
 * To change this template use File | Settings | File Templates.
 */
public class CustomerMapper {
    public static Customer toEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        return customer;
    }

    public static CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhone(customer.getPhone());
        return customerDto;
    }

    public static CustomerDetailsDto toCustomerDetailsDto(Customer customer) {

        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();

        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setMobileNumber(customer.getPhone());

        return customerDetailsDto;
    }
}
