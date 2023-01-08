package com.gznznzjsn.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * todo fixes:
 * add versions to separate block in pom
 *
 *маппер опять не работает, т к пагин убран и дто переделано
 *
 * продумать какие операции я вообще хочу иметь
 *валидация дто
 *          и моделей
 *
 *
 *
 * todo requests:
1. Пост Список заданий и комментариев, а также дата прибытия - примерная общая стоимость, рекомендуемые по времени стотрудники ,примерная дата окончания
2. Гет Айди пользователя - список заказов (названия заданий, статус, дата окончания)
3. гет Ай ди заказа - более подробная инфа о заказе
4. делит ай ди заказа - удаление заказа, если не в процессе уже
5. Подпись работника на заказ-> обновленная цена, и коммент от работника->автоматическое проставление времени начала работы
6. гет айди работника - заказов работника и время их начала, а также комментарии
 * */
@SpringBootApplication
public class CarServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(CarServiceApplication.class, args);
    }

}
