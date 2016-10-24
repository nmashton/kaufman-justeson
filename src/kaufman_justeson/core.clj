;; Threads the data file through the transformations
;; defined in [kaufman.transducers](#kaufman.transducers).
;; Produces a list of lexemes tagged with metadata.
(ns kaufman-justeson.core
  (:use [kaufman-justeson.transducers]))

(def processed
  (with-open [rdr (clojure.java.io/reader "./data/kaufman-footers-stripped.txt")]
    (doall
      (eduction
        (comp
          strip-percents
          group-with-semantic-field-block
          split-on-xx-delimiters
          split-on-eq-delimiters
          eliminate-garbage
          eliminate-more-garbage
          eliminate-even-more-garbage
          group-by-root-lines
          split-on-blank-lines
          turn-lines-into-maps
          remove-blank-lexemes)
        (line-seq rdr)))))
