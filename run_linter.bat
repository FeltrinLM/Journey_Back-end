@echo off

REM *** 1. Configurações do PROJETO (Onde está o seu código) ***
SET PROJECT_DIR=C:\Users\loren\IdeaProjects\Journey_Back-end
SET SOURCE_DIR=%PROJECT_DIR%\src

REM *** 2. Configuração da FERRAMENTA PMD (ATENÇÃO AQUI) ***
REM Coloque abaixo o caminho COMPLETO de onde está o arquivo pmd.bat no seu PC.
REM Exemplo: C:\Users\loren\Downloads\PMD\pmd-dist-7.17.0-bin\pmd-bin-7.17.0\bin\pmd.bat
SET PMD_EXECUTABLE="C:\Users\loren\IdeaProjects\Journey_Back-end\PMD\pmd-dist-7.17.0-bin\pmd-bin-7.17.0\bin\pmd.bat"

REM *** 3. Configurações de Regra e Saída ***
SET RULESET=rulesets/java/quickstart.xml
SET REPORT_FILE=%PROJECT_DIR%\pmd-report.html

REM *** 4. Executa o comando PMD ***
echo.
echo ========================================================
echo Analisando projeto: Journey_Back-end
echo ========================================================
echo.

IF NOT EXIST %PMD_EXECUTABLE% (
    echo [ERRO] Nao encontrei o PMD no caminho: %PMD_EXECUTABLE%
    echo Por favor, edite este arquivo .bat e corrija a linha "SET PMD_EXECUTABLE"
    pause
    exit /b
)

echo Rodando PMD Linter na pasta: %SOURCE_DIR%
call %PMD_EXECUTABLE% check -d "%SOURCE_DIR%" -R %RULESET% -f html -r "%REPORT_FILE%"

echo.
echo Linter concluido!
echo Relatorio salvo em: %REPORT_FILE%
echo.

echo Tentando abrir o relatorio no seu navegador...
start "" "%REPORT_FILE%"

pause