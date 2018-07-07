(ns naive-bayes.core-test
  (:require [clojure.test :refer :all]
            [naive-bayes.core :refer :all]))

(defn long-str [& strings]
  (clojure.string/join "\n" strings))

(def data
  (long-str
    "Иван Мужской"
    "Пётр Мужской"
    "Валерий Мужской"
    "Александр Мужской"
    "Алёна Женский"
    "Мария Женский"
    "Маргарита Женский"
    "Александра Женский"))

(def samples
  (->>
    (clojure.string/split-lines data)
    (map #(clojure.string/split % #"\s"))))

(defn get-features [sample]
  {:last-letter (last sample)
   :first-letter (first sample)
   :second-letter (second sample)})

(def features
  (->> samples
    (map (fn [w] [(get-features (first w)) (second w)]))))

(def classifier
  (train features))

(deftest a-test
  (testing "Main functional test"
    (is (= (classify classifier (get-features "Агафья")))
           "Женский")))
