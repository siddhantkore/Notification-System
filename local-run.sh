#!/bin/bash

APP_NAME="NOTIFICATION-SYSTEM"
BUILD_LOG="build.log" # add this to file in gitignore to exclude it in commit

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
CYAN='\033[0;36m'
BOLD='\033[1m'
NC='\033[0m'

echo -e "${CYAN}============================================================================${NC}"
echo -e "${CYAN}||                          ${BOLD}${APP_NAME}${NC}                               ${CYAN}||${NC}"
echo -e "${CYAN}============================================================================${NC}"

echo -e ""
echo -e "                         ${YELLOW}STEP 1: BUILDING PACKAGE${NC}"
echo -e "${GREEN}------------------------------------------------------------------------------${NC}"
# Remove old log
rm -f "$BUILD_LOG"

# Run mvn build and save output to log
mvn clean package | tee "$BUILD_LOG"

# Check build status if there is an error
if [ "${PIPESTATUS[0]}" -ne 0 ]; then
    echo -e ""
    echo -e "${RED}*********************************************************"
    echo -e "                             ${RED}ERROR: BUILD FAILURE!${NC}"
    echo -e "${RED}      Check ---> '${BOLD}${YELLOW}$BUILD_LOG${NC}' ${RED}<--- for details.${NC}"
    echo -e "${RED}*********************************************************${NC}"
    exit 1
else
    echo -e ""
    echo -e "                        ${GREEN}${BOLD}BUILD SUCCESSFUL${NC}"
fi

echo -e ""
echo -e "${GREEN}------------------------------------------------------------${NC}"
echo -e "                      ${YELLOW}STEP 2: STARTING APPLICATION${NC}"
echo -e "${GREEN}------------------------------------------------------------${NC}"
mvn spring-boot:run
