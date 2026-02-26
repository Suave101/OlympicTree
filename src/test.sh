#!/bin/bash

# 1. Compile all Java files
echo "Compiling..."
javac *.java

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

# 2. Run tests
for i in 1 2 3 4 5; do
    echo "---------------------------"
    echo "Running Test Case $i..."
    echo "---------------------------"

    java HW3 hw3in${i}data.txt hw3in${i}queries.txt > my_output$i.txt

    # Compare the output with the expected output
    diff my_output$i.txt hw3out$i.txt > diff_result.txt

    if [ $? -eq 0 ]; then
        echo "TEST $i: PASSED! ✅"
    else
        echo "TEST $i: FAILED! ❌"
        echo "Differences found between my_output$i.txt and hw3out$i.txt:"
        cat diff_result.txt
    fi
    echo "---------------------------"
    echo ""
done