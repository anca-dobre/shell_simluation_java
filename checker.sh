red="\e[31m"
green="\e[32m"
yellow="\e[93m"
clr="\e[39m"

function checkDiff {
    if [ $res_out -eq 0 ]
    then
        echo -en "$green" "output OK" "$clr" "......"
    else
        echo -en "$red" "output WRONG" "$clr" "..."
    fi
    
    if [ $res_err -eq 0 ]
    then
        echo -en "$green" "errors OK" "$clr" "......"
    else
        echo -en "$red" "errors WRONG" "$clr" "..."
    fi
    
    if [ $res_out -eq 0 ] && [ $res_err -eq 0 ]
    then
        points=$((points + $1))
        echo -e "$1/$1"
    else
        echo -e "0/$1"
    fi
}

function test {
    points=0

    # Run main tests
    for i in `seq 1 10`
    do
        java Main "tests/in/input$i" output errors
        
        diff --strip-trailing-cr output "tests/ref/output$i" 1>/dev/null
        res_out=$?
        diff --strip-trailing-cr errors "tests/ref/errors$i" 1>/dev/null
        res_err=$?
        
        echo -en "TEST $i ........."
        checkDiff 9
    done
    
    # Run bonus tests
    # bouns 1
    echo 
    java Main "tests/in/input1_bonus1" output errors
        
    diff --strip-trailing-cr output "tests/ref/output1_bonus1" 1>/dev/null
    res_out=$?
    diff --strip-trailing-cr errors "tests/ref/errors1_bonus1" 1>/dev/null
    res_err=$?
    
    echo -en "BONUS 1 ........."
    checkDiff 5
    
    # bonus 2
    java Main "tests/in/input1_bonus2" output errors
        
    diff --strip-trailing-cr output "tests/ref/output1_bonus2" 1>/dev/null
    res_out=$?
    diff --strip-trailing-cr errors "tests/ref/errors1_bonus2" 1>/dev/null
    res_err=$?
    
    echo -en "BONUS 2 ........."
    checkDiff 15
    
    echo -e "\nREADME + JavaDoc ......... TO BE CHECKED ...... 10/10"
    points=$((points + 10))
    
    echo -e "\nTOTAL: $points/100"
}

echo -e "-------------------- PowerBash ---------------------"

echo -e "$yellow" "\nBuilding sources..." "$clr"
make build

echo -e "$yellow" "\nRunning tests...\n" "$clr"
test

echo -e "\n----------------------------------------------------"

make clean
rm -f output errors
