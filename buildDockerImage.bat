@echo off

for %%i in (account-api customer-api) do (
     docker build -t "mboaeat/%%i:latest" %%i
)