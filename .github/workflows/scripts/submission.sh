#!/bin/bash

make
echo -e "Running tests..."
ERR=0
for i in $(seq -f "%02g" 1 15)
do
    java winzigc -ast winzig_test_programs/winzig_${i} > tree.${i}
    DIFF=$(diff tree.${i} winzig_test_programs/winzig_${i}.tree)

    if [ "$DIFF" == "" ]; then
      echo "Test winzig_${i}: ${green}Passed${reset}"
    else
      echo "Test winzig_${i}: ${red}Failed${reset}"
      ERR=$((ERR + 1))
    fi
done
[ $ERR -gt 0 ] && echo -e "\n${red}There are test failures${reset}"
exit ${ERR}
