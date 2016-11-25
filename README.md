# Conways Game of Life Clojure

I'm pretty new to Clojure and decided to try solve Conway's Game of Life in it.
This was my first attempt without looking at any other implementations.

## Installation

lein deps

## Usage

If you use Vim / Tmux, you can bind the following key to load all the files.
Open a Tmux split below your editor and run `lein repl`

Map the following key (I used x) to load all the files and run the tests.

nnoremap <leader>x :silent !tmux send-keys -t 2 "(require '[clojure.test :refer [run-tests]]) (require 'conways-gol.core :reload) (require 'conways-gol.core-test :reload) (run-tests 'conways-gol.core-test)" C-m<cr><cr>

Once this is done you can run
`(-main)` to start the game with the Glider defined at the top of the file.
