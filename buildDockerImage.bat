@echo off

for %%i in (account-api) do (
     docker build -t "mboaeat/%%i:latest" %%i
)