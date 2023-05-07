#!/bin/bash

# script variables
MAIN_DIR=$(pwd)
BUILD_DIR="${MAIN_DIR}/out/"
TREES_DIR="${MAIN_DIR}/trees/"

#colors
red=$(tput setaf 1)
green=$(tput setaf 2)
cyan=$(tput setaf 6)
reset=$(tput sgr0)

make
mkdir -p $TREES_DIR
cd $BUILD_DIR

echo -e "Running tests..."
ERR=0
cd "${BUILD_DIR}" &&
for i in $(seq -f "%02g" 1 15)
do
    java winzigc -ast ../winzig_test_programs/winzig_${i} > ${TREES_DIR}tree.${i}
    DIFF=$(diff ${TREES_DIR}tree.${i} ../winzig_test_programs/winzig_${i}.tree)

    if [ "$DIFF" == "" ]; then
      echo "Test winzig_${i}: ${green}Passed${reset}"
    else
      echo "Test winzig_${i}: ${red}Failed${reset}"
      ERR=$((ERR + 1))
    fi
done
[ $ERR -gt 0 ] && echo -e "\n${red}There are test failures${reset}"

cd ..
rm -rf $BUILD_DIR $TREES_DIR
exit ${ERR}
