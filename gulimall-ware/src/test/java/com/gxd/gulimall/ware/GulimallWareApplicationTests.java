package com.gxd.gulimall.ware;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class GulimallWareApplicationTests {

    @Test
    void contextLoads() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        String commaSeparatedNumbers = numbers.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(", "));

        System.out.println(commaSeparatedNumbers);
    }

}
