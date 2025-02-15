package example.numbersfind.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class NumberService {

    public Integer findNumberMaxFromFileByNth(String filePath, int n) throws IOException {

        if (n <= 0) {
            throw new IllegalArgumentException("N должно быть положительным числом.");
        }

        List<Integer> numbers = readNumbersFromFile(filePath);
        if (numbers.size() < n) {
            throw new IllegalArgumentException("В файле недостаточно чисел для поиска N-ного максимального числа.");
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);
        for (int num : numbers) {
            if (minHeap.size() < n) {
                minHeap.add(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.add(num);
            }
        }
        return minHeap.peek();
    }

    private List<Integer> readNumbersFromFile(String filePath) throws IOException {

        try (FileInputStream file = new FileInputStream(new File(filePath));

             Workbook workbook = new XSSFWorkbook(file)) {

            List<Integer> numbers = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        numbers.add((int) cell.getNumericCellValue());
                    }
                }
            }

            return numbers;
        }
    }
}
