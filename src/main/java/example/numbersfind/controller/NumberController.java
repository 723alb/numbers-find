package example.numbersfind.controller;

import example.numbersfind.service.NumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Tag(name = "Number Controller", description = "Контроллер для работы с числами из файла")
public class NumberController {

    private final NumberService numberService;

    @Autowired
    public NumberController(NumberService numberService) {
        this.numberService = numberService;
    }

    @PostMapping("/findNumberMaxFromFileByNth")
    @Operation(
            summary = "Найти N-ное максимальное число из файла",
            description = "Метод принимает путь к файлу и число N, возвращает N-ное максимальное число из файла.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный результат"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public Integer findMax(

            @Parameter(description = "Путь к файлу .xlsx", example = "./numbers.xlsx")
            @RequestParam String filePath,

            @Parameter(description = "Число N (например, 2 для второго максимального числа)", example = "2")
            @RequestParam int n
    ) throws IOException {
        return numberService.findNumberMaxFromFileByNth(filePath, n);
    }


}
