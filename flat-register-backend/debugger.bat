@echo off

set file=debug.txt

if EXIST %file% (

  del debug.txt
  echo File deleted !
  git status
  git add . && git commit -m "supratim531: debugging..."
  git status
  git push

) else (

  echo This is a debug file > debug.txt
  echo File created !
  git status
  git add . && git commit -m "supratim531: debugging..."
  git status
  git push

)

pause
