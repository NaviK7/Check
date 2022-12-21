package org.example.runner;

import file.WriteFileCheck;
import org.example.dao.model.Check;
import org.example.dao.model.CheckLine;
import org.example.dao.model.DiscountCard;
import org.example.dao.repository.RepositoryCard;
import org.example.dao.repository.RepositoryCardImpl;
import org.example.service.ServiceCheck;
import org.example.service.ServiceCheckImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Runner {
    private static final ServiceCheck serviceCheck = new ServiceCheckImpl();
    private static final RepositoryCard repositoryCard = new RepositoryCardImpl();
    private final static WriteFileCheck WRITE_FILE_CHECK = new WriteFileCheck();

    public static void CheckRunner(String text, String card) throws IOException {
        List<CheckLine> checkLineList = new ArrayList<>();

        //проверяем входной текст на пробелы и убираем лишние
        StringBuilder stringBuilder = new StringBuilder();
        text = text.trim();
        text = text.replaceAll("\\s+", " ");
        stringBuilder.append(text.charAt(0));
        for (int i = 1; i < text.length() - 1; i++) {
            if (text.charAt(i) == ' ' & text.charAt(i - 1) == '-'
                    || text.charAt(i) == ' ' & text.charAt(i + 1) == '-') {
                continue;
            }
            stringBuilder.append(text.charAt(i));
        }
        stringBuilder.append(text.charAt(text.length() - 1));
        text = stringBuilder.toString();
        text = text.replaceAll(" ", ":");
        String[] products = text.split(":");

        //создаем спсок продуктов по id продукта  и его количеству
        for (String s : products) {
            String[] product = s.split("-");
            if (Character.isLetter(product[0].charAt(0)) || Character.isLetter(product[1].charAt(0))) {
                throw new RuntimeException("Вы ввели букву вместо номера товара или его количества");
            }
            checkLineList.add(serviceCheck.creatCheckLine(Integer.parseInt(product[1]), Integer.parseInt(product[0])));
        }

        //проверяем входной номер скидочной карты
        card = card.replaceAll(" ", "");
        if (card.length() < 5 && !card.equals("")) {
            for (int i = 0; i < card.length(); i++) {
                if (Character.isLetter(card.charAt(i))) {
                    throw new RuntimeException("Вы ввели букву вместо цифры");
                }
            }

            //извлекаем из репозитория нужную карту
            Optional<DiscountCard> discountCardOptional = repositoryCard.findOneDiscountCard(Integer.parseInt(card));
            if (discountCardOptional.isPresent()) {
                DiscountCard discountCard = discountCardOptional.get();

                //создаем чек и отправляем в него список требуемых товаров
                Check check = serviceCheck.creatCheck(checkLineList, discountCard);
                //вывод вреиени совершения покупки
                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat(" yyyy.MM.dd ");
                Date dateNow1 = new Date();
                SimpleDateFormat formatForDateNow1 = new SimpleDateFormat(" HH:mm:ss ");
                WRITE_FILE_CHECK.WriteToFile("DATE: " + formatForDateNow.format(dateNow));
                System.out.println("DATE: " + formatForDateNow.format(dateNow));
                WRITE_FILE_CHECK.WriteToFile("TIME: " + formatForDateNow1.format(dateNow1));
                System.out.println("TIME: " + formatForDateNow1.format(dateNow1));
                System.out.println(check);
                WRITE_FILE_CHECK.WriteToFile(check.toString().replace("[", "").replace("]", "").replace(",", ""));

                String firstResult = serviceCheck.getCommonPriceCheck(check) + "";
                System.out.println("Суммарная стоимость всех покупок: " + firstResult + " BYN");
                WRITE_FILE_CHECK.WriteToFile("Суммарная стоимость всех покупок: " + firstResult + " BYN");

                String secondResult = "" + serviceCheck.getCommonPriceDiscount(check);
                System.out.println("Суммарная скидка от стоимости всех покупок: " + secondResult + " BYN");
                WRITE_FILE_CHECK.WriteToFile("Суммарная скидка от стоимости всех покупок: " + secondResult + " BYN");

                if (checkingDiscountStatus(check)) {
                    String thirdResult = serviceCheck.getTotalPriceWithStock(check) + "";
                    System.out.println("Итого с учетом скидки по акции: " + thirdResult + " BYN");
                    WRITE_FILE_CHECK.WriteToFile("Итого с учетом скидки по акции: " + thirdResult + " BYN");
                } else {
                    String thirdResult = serviceCheck.getTotalPrice(check) + "";
                    System.out.println("Итого: " + thirdResult + " BYN");
                    WRITE_FILE_CHECK.WriteToFile("Итого:  " + thirdResult + " BYN");
                }
                WRITE_FILE_CHECK.WriteToFile("--------------------------------------------------------------------" + "\n");
            } else throw new NullPointerException("Скидочной карты с таким номером нет");
        } else if (card.equals("")) {
            //создаем чек и отправляем в него список требуемых товаров
            Check check = serviceCheck.creatCheck(checkLineList);
            //вывод вреиени совершения покупки
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat(" yyyy.MM.dd ");
            Date dateNow1 = new Date();
            SimpleDateFormat formatForDateNow1 = new SimpleDateFormat(" HH:mm:ss ");
            WRITE_FILE_CHECK.WriteToFile("DATE: " + formatForDateNow.format(dateNow));
            System.out.println("DATE: " + formatForDateNow.format(dateNow));
            WRITE_FILE_CHECK.WriteToFile("TIME: " + formatForDateNow1.format(dateNow1));
            System.out.println("TIME: " + formatForDateNow1.format(dateNow1));
            System.out.println(check);
            WRITE_FILE_CHECK.WriteToFile(check.toString().replace("[", "").replace("]", "").replace(",", ""));
            String firstResult = serviceCheck.getCommonPriceCheck(check) + "";
            System.out.println("Суммарная стоимость всех покупок: " + firstResult + " BYN");
            WRITE_FILE_CHECK.WriteToFile("Суммарная стоимость всех покупок: " + firstResult + " BYN");
            if (checkingDiscountStatus(check)) {
                String thirdResult = serviceCheck.getTotalPriceWithStock(check) + "";
                System.out.println("Итого с учетом скидки по акции: " + thirdResult + " BYN");
                WRITE_FILE_CHECK.WriteToFile("Итого с учетом скидки по акции: " + thirdResult + " BYN");
            } else {
                String thirdResult = serviceCheck.getTotalPrice(check) + "";
                System.out.println("Итого: " + thirdResult + " BYN");
                WRITE_FILE_CHECK.WriteToFile("Итого:  " + thirdResult + " BYN");
            }
            WRITE_FILE_CHECK.WriteToFile("--------------------------------------------------------------------" + "\n");
        } else throw new RuntimeException("Нужно ввести четыре цифры");
    }

    public static boolean checkingDiscountStatus(Check check) {
        boolean stockStatus = false;
        for (CheckLine checkLine : check.getCheckLineList()) {
            if (checkLine.getProduct().isDiscountStatus() && checkLine.getCountProduct() > 5) {
                stockStatus = true;
                break;
            }
        }
        return stockStatus;
    }

    public static void Run() {
        boolean continuation = true;
        while (continuation) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите количество товара и его id в формате itemId-quantity (itemId - идентификатор товара, quantity - его количество).Например: 1-2 2-3 6-2");
                String itemIdText = scanner.nextLine();
                System.out.println("Введите номер скидочной карты, состоящий из четырех цифр");
                String cardText = scanner.nextLine();
                System.out.println("Ваш чек");
                System.out.println();
                CheckRunner(itemIdText, cardText);
                System.out.println();
                System.out.println("Желаете продолжить? Если да - введите цифру 1, если нет - любую другую");
                String continuationText = scanner.nextLine();
                if (continuationText.equals("1")) {
                    continuation = true;
                } else {
                    continuation = false;
                }
            } catch (RuntimeException exception) {
                System.out.println("Проверте вводимые данные");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

